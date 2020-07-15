package com.mushr00m.dao;

import com.mushr00m.entity.TbRoom;
import com.mushr00m.model.RoomDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbRoomMapper extends Mapper<TbRoom> {
    List<RoomDetail> selectByUserId(@Param("userid") int userid,
                                   @Param("state")int state);

}