package com.example.guyunwu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "setting")
public class SettingEntity {

    @Column(name = "type", isId = true, autoGen = false)
    private Integer settingType;

    @Column(name = "boolean_data")
    private Boolean booleanData;

    @Column(name = "string_data")
    private String stringData;

    @Column(name = "integer_data")
    private Integer integerData;

    @Column(name = "double_data")
    private Double doubleData;

    @Column(name = "date_data")
    private Date dateData;
}
