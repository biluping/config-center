package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {

    @ColumnComment("主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ColumnComment("创建时间")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @ColumnComment("修改时间")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    @ColumnComment("逻辑删除,0-未删除,1-已删除")
    @ColumnDefault("0")
    @TableLogic
    private Integer deleted;
}
