package com.armybuilderv2.armyBuilderV2.unitStats;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UnitStats {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer m;
    private Integer ws;
    private Integer bs;
    private Integer s;
    private Integer t;
    private Integer w;
    private Integer i;
    private Integer a;
    private Integer ld;
    private Integer basicSave;
    private Integer wardSave;








}

