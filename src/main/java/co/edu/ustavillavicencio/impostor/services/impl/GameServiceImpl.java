package co.edu.ustavillavicencio.impostor.services.impl;

import co.edu.ustavillavicencio.impostor.dtos.response.*;
import co.edu.ustavillavicencio.impostor.entities.AssignmentEntity;
import co.edu.ustavillavicencio.impostor.entities.PlayerEntity;
import co.edu.ustavillavicencio.impostor.entities.RoomEntity;
import co.edu.ustavillavicencio.impostor.entities.VoteEntity;
import co.edu.ustavillavicencio.impostor.enums.Role;
import co.edu.ustavillavicencio.impostor.enums.RoomStatus;
import co.edu.ustavillavicencio.impostor.repositories.AssignmentRepository;
import co.edu.ustavillavicencio.impostor.repositories.PlayerRepository;
import co.edu.ustavillavicencio.impostor.repositories.RoomRepository;
import co.edu.ustavillavicencio.impostor.repositories.VoteRepository;
import co.edu.ustavillavicencio.impostor.services.GameService;
import co.edu.ustavillavicencio.impostor.services.StaticResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final StaticResourceService staticResourceService;
    private final RoomRepository roomRepo;
    private final PlayerRepository playerRepo;
    private final AssignmentRepository assignmentRepo;
    private final VoteRepository voteRepo;

    @Override
    public GameStartResponse start(String roomCode, UUID hostPlayerId) {
        Random random = new Random();

        RoomEntity room = findRoomByCode(roomCode);

        if (!room.getHostPlayerId().equals(hostPlayerId)) {
            throw new IllegalArgumentException("Invalid host player ID");
        }

        List<String> wordList = room.getCategory().getWordList(staticResourceService);
        List<PlayerEntity> roomPlayers = findRoomPlayers(roomCode)
                .stream()
                .filter(PlayerEntity::isAlive)
                .toList();

        room.setSecretWord(wordList.get(random.nextInt(wordList.size())));

        if (roomPlayers.size() < 3) {
            throw new RuntimeException("Not enough players");
        }

        for (int i = 0; i < room.getImpostorCount();) {
            PlayerEntity p = roomPlayers.get(random.nextInt(roomPlayers.size()));

            if (findAssignment(roomCode, p.getId()) != null) {
                continue;
            }

            createAssignment(roomCode, p.getId(), Role.IMPOSTOR, null);

            i++;
        }

        for (PlayerEntity player : roomPlayers) {
            if (findAssignment(roomCode, player.getId()) != null) {
                continue;
            }

            createAssignment(roomCode, player.getId(), Role.CIVIL, room.getSecretWord());
        }

        room.setStatus(RoomStatus.IN_GAME);
        room.setCurrentRound(1);

        return new GameStartResponse(room.getStatus().getLabel(), room.getCurrentRound());
    }

    @Override
    public MeResponse me(String roomCode, UUID playerId) {
        AssignmentEntity a = findAssignment(roomCode, playerId);
        if (a == null) { throw new IllegalArgumentException("No assignment for " + roomCode + " " + playerId); }
        String word = a.getRole() == Role.CIVIL ? a.getWord() : null;

        return new MeResponse(a.getRole().getLabel(), a.getWord());
    }

    @Override
    public VoteCreateResponse votes(String roomCode, UUID voterId, UUID votedId) {
        RoomEntity room = findRoomByCode(roomCode);
        if (!room.getStatus().equals(RoomStatus.IN_GAME)) {
            throw new IllegalArgumentException("Invalid room status");
        }

        PlayerEntity voter = findPlayer(voterId);
        if (!(voter.isAlive() && voter.getRoomId().equals(room.getId()))) {
            throw new IllegalArgumentException("Invalid voter ID");
        }

        PlayerEntity voted = findPlayer(votedId);
        if (!(voted.isAlive() && voted.getRoomId().equals(room.getId()))) {
            throw new IllegalArgumentException("Invalid voted ID");
        }

        if (voteExists(voterId, room.getCurrentRound())) {
            throw new IllegalArgumentException("Vote already cast");
        }

        createVote(room, voterId, votedId);

        return new VoteCreateResponse("Voto recibido", room.getCurrentRound());
    }

    @Override
    public RoundCloseResponse roundClose(String roomCode, UUID hostPlayerId) {
        RoomEntity room = findRoomByCode(roomCode);
        List<PlayerEntity> roomPlayers = findRoomPlayers(roomCode);

        if (!room.getHostPlayerId().equals(hostPlayerId)) {
            throw new IllegalArgumentException("Invalid host player ID");
        }

        Map<UUID, Integer> playerVotes = new HashMap<>();
        List<VoteEntity> votes = voteRepo.findAll();
        final AtomicInteger maxVotes = new AtomicInteger(0);

        for (VoteEntity vote : votes) {
            Integer voteCount = playerVotes.get(vote.getId());
            if (voteCount == null) voteCount = 0;
            if (++voteCount > maxVotes.get()) maxVotes.set(voteCount);
            playerVotes.put(vote.getVotedId(), voteCount);
        }

        UUID expelled = null;
        List<UUID> topVoted = playerVotes.entrySet().stream()
                .filter(entry -> entry.getValue() == maxVotes.get())
                .map(Map.Entry::getKey)
                .toList();

        if (topVoted.size() == 1) {
            expelled = topVoted.getFirst();
        }

        expelled = topVoted.get((new Random()).nextInt(topVoted.size()));

        PlayerEntity playerExpelled = expel(expelled);
        boolean playerExpelledWasImpostor = findRole(roomCode, playerExpelled.getId()).equals(Role.IMPOSTOR);

        if (playerExpelledWasImpostor) {
            room.setStatus(RoomStatus.FINISHED);
            return new RoundGameOverResponse(
                    Role.CIVIL.getLabel(),
                    room.getSecretWord(),
                    roomPlayers.stream().map(p ->
                            new PlayerRevealResponse(p.getId(), p.getNickname(), findRole(roomCode, p.getId()).getLabel())
                    ).toList()
            );
        }

        if (
            roomPlayers.size() < 3 &&
            roomPlayers.stream()
                    .filter(p -> findRole(roomCode, p.getId()).equals(Role.IMPOSTOR))
                    .anyMatch(PlayerEntity::isAlive)
        ) {
            room.setStatus(RoomStatus.FINISHED);
            return new RoundGameOverResponse(
                    Role.IMPOSTOR.getLabel(),
                    room.getSecretWord(),
                    roomPlayers.stream().map(p ->
                            new PlayerRevealResponse(p.getId(), p.getNickname(), findRole(roomCode, p.getId()).getLabel())
                    ).toList()
            );
        }

        int currentRound = room.getCurrentRound() + 1;
        room.setCurrentRound(currentRound);

        return new RoundContinueResponse(
                new PlayerExpelledResponse(playerExpelled.getId(), playerExpelled.getNickname(), playerExpelledWasImpostor),
                currentRound + 1,
                (int) roomPlayers.stream()
                        .filter(PlayerEntity::isAlive)
                        .count()
        );
    }

    private RoomEntity findRoomByCode(String code) {
        return roomRepo.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException(("RoomEntity not found " + code)));
    }

    private PlayerEntity findPlayer(UUID id) {
        return playerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("PlayerEntity not found " + id)));
    }

    private AssignmentEntity findAssignment(String roomCode, UUID playerId) {
        RoomEntity r = findRoomByCode(roomCode);
        PlayerEntity p = findPlayer(playerId);

        try {
            return assignmentRepo.findAll().stream()
                    .filter(a -> a.getRoomId().equals(r.getId()) && a.getPlayerId().equals(p.getId()))
                    .toList()
                    .getFirst();
        } catch (NoSuchElementException e) { return null; }
    }

    private List<PlayerEntity> findRoomPlayers(String roomCode) {
        RoomEntity room = findRoomByCode(roomCode);
        return playerRepo.findAll().stream()
                .filter(p -> p.getRoomId().equals(room.getId()))
                .toList();
    }

    private void createAssignment(String roomCode, UUID playerId, Role role, String word) {
        RoomEntity r = findRoomByCode(roomCode);
        AssignmentEntity a = new AssignmentEntity(null, r.getId(), playerId, role, word);
        assignmentRepo.save(a);
    }

    private boolean voteExists(UUID voterId, int roundNumber) {
        return voteRepo.findAll().stream()
                .anyMatch(
                        v -> v.getVoterId().equals(voterId) &&
                        v.getRoundNumber() == roundNumber
                );
    }

    private void createVote(RoomEntity room, UUID voterId, UUID votedId) {
        voteRepo.save(new VoteEntity(null, room.getId(), room.getCurrentRound(), voterId, votedId));
    }

    private Role findRole(String roomCode, UUID playerId) {
        AssignmentEntity assignment = findAssignment(roomCode, playerId);
        if (assignment == null) throw new IllegalArgumentException("No assignment for " + roomCode + " " + playerId);
        return assignment.getRole();
    }

    private PlayerEntity expel(UUID expelled) {
        PlayerEntity p = findPlayer(expelled);
        p.setAlive(false);
        return p;
    }
}
