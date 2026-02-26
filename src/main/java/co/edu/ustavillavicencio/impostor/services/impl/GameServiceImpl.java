package co.edu.ustavillavicencio.impostor.services.impl;

import co.edu.ustavillavicencio.impostor.dtos.response.MeResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoundCloseResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.StartResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.VotesResponse;
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
    public StartResponse start(String roomCode, UUID hostPlayerId) {
        Random random = new Random();

        RoomEntity room = findRoomByCode(roomCode);

        if (!room.getHostPlayerId().equals(hostPlayerId)) {
            throw new IllegalArgumentException("Invalid host player ID");
        }

        List<String> wordList = room.getCategory().getWordList(staticResourceService);
        List<PlayerEntity> roomPlayers = findRoomPlayers(room.getId())
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

        return new StartResponse(room.getStatus().getLabel(), room.getCurrentRound());
    }

    @Override
    public MeResponse me(String roomCode, UUID playerId) {
        AssignmentEntity a = findAssignment(roomCode, playerId);
        if (a == null) { throw new IllegalArgumentException("No assignment for " + roomCode + " " + playerId); }
        String word = a.getRole() == Role.CIVIL ? a.getWord() : null;

        return new MeResponse(a.getRole().getLabel(), a.getWord());
    }

    @Override
    public VotesResponse votes(String roomCode, UUID voterId, UUID votedId) {
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

        return new VotesResponse("Voto recibido", room.getCurrentRound());
    }

    @Override
    public RoundCloseResponse roundClose(String roomCode, UUID hostPlayerId) {
        Map<UUID, Integer> playerVotes = new HashMap<>();
        List<VoteEntity> votes = voteRepo.findAll();

        for (VoteEntity vote : votes) {
            if (playerVotes.get(vote.getId()));
        }

        return null;
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

    private List<PlayerEntity> findRoomPlayers(UUID roomId) {
        return playerRepo.findAll().stream()
                .filter(p -> p.getRoomId().equals(roomId))
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
}
