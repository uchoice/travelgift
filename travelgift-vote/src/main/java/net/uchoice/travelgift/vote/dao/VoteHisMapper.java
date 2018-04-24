package net.uchoice.travelgift.vote.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.uchoice.travelgift.vote.entity.VoteHis;

@Mapper
public interface VoteHisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_vote_his
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_vote_his
     *
     * @mbggenerated
     */
    int insert(VoteHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_vote_his
     *
     * @mbggenerated
     */
    VoteHis selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_vote_his
     *
     * @mbggenerated
     */
    List<VoteHis> selectAll();
    
    int selectCount(String userId, Date voteDate);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_vote_his
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(VoteHis record);
}