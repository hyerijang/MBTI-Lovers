package kr.hogink.mbti.MBTILovers.web.chat;


import org.springframework.stereotype.Repository;

import java.util.*;


public class RoomRepositoryMem implements RoomRepository {

    private static Map<Long, Room> chatRoomMap; //메모리에 저장
    private static Long sequence = 0L;

    RoomRepositoryMem() {
        chatRoomMap = new LinkedHashMap<>();
    }


    @Override
    public List<Room> findAll() {
        // 채팅방 생성순서 최근 순으로 반환
        List rooms = new ArrayList(chatRoomMap.values());
        Collections.reverse(rooms);

//        for (String key : chatRoomMap.keySet()) {
//            String value = chatRoomMap.get(key).getName();
//            System.out.println("[key]:" + key + ", [value]:" + value);
//        }
        return rooms;
    }

    @Override
    public Optional<Room> findRoomByRid(Long rid) {
        return Optional.of(chatRoomMap.get(rid));
    }

    @Override
    public Room save(Room room) {
        room.setRid(sequence++);
        chatRoomMap.put(room.getRid(), room);
        return room;
    }
}