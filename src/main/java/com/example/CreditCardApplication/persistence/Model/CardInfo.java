package com.example.CreditCardApplication.persistence.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "card_limit")
    private Long limit;

    @Column(name = "balance")
    private Long balance;
}
