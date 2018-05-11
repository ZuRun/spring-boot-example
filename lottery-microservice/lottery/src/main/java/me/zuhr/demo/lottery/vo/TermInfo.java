package me.zuhr.demo.lottery.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zurun
 * @date 2018/5/12 01:01:44
 */
@Data
public class TermInfo {
    /**
     * 当前期数
     */
    private String openCount;

    /**
     * 当前开奖号
     */
    private String openResult;

    /**
     * 当前期号
     */
    private String openTerm;

    /**
     * 当前期号开奖时间
     */
    private Date operResultTime;

    /**
     * 正在开奖期数
     */
    private String openningTerm;

    /**
     * 正在投注的期数
     */
    private String currentTerm;

    /**
     * 剩余期数
     */
    private String waitCount;
}
