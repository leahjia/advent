use std::collections::{HashSet, HashMap, VecDeque};
use lazy_static::lazy_static;

lazy_static!(
    static ref FILE: String = std::fs::read_to_string("../input/day4.txt").unwrap();
    );

fn main() {
    let (decks, winning_cards) = parse_input(&FILE);
    println!("{}{}", "Expect 21821, Result: ", part1(&decks, &winning_cards));
    println!("{}{}", "Expect 5539496, Result: ", part2(&decks, &winning_cards));
}

fn parse_input(input: &str) -> (Vec<Vec<i32>>, Vec<HashSet<i32>>) {
    let mut winning_cards: Vec<HashSet<i32>> = Vec::new();
    let mut decks: Vec<Vec<i32>> = Vec::new();

    for line in input.lines() {
        let all_cards: Vec<_> = line.splitn(2, ": ").nth(1).unwrap().split(" | ").collect();
        winning_cards.push(all_cards[0].split_whitespace().map(|card| card.parse::<i32>().unwrap()).collect());
        decks.push(all_cards[1].split_whitespace().map(|card| card.parse::<i32>().unwrap()).collect());
    }
    (decks, winning_cards)
}

fn part1(decks: &[Vec<i32>], winning_cards: &[HashSet<i32>]) -> i32 {
    let mut res = 0;
    for (i, deck) in decks.iter().enumerate() {
        let mut ct = 0;
        for card in deck {
            if winning_cards[i].contains(card) {
                ct = if ct == 0 { 1 } else { ct * 2 };
            }
        }
        res += ct;
    }
    res
}

fn part2(decks: &[Vec<i32>], winning_cards: &[HashSet<i32>]) -> i32 {
    let mut matches = HashMap::new();
    for (i, deck) in decks.iter().enumerate() {
        for card in deck {
            if winning_cards[i].contains(card) {
                *matches.entry(i + 1).or_insert(0) += 1;
            }
        }
    }

    let mut res = decks.len() as i32;
    let mut q: VecDeque<_> = matches.keys().cloned().collect();
    while let Some(curr) = q.pop_front() {
        for i in 1..=*matches.get(&curr).unwrap() {
            if matches.contains_key(&(curr + i)) {
                q.push_back(curr + i);
            }
            res += 1;
        }
    }
    res
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day4_part1() {
        let (decks, winning_cards) = parse_input(&FILE);
        assert_eq!(21821, part1(&decks, &winning_cards))
    }

    #[test]
    fn day4_part2() {
        let (decks, winning_cards) = parse_input(&FILE);
        assert_eq!(5539496, part2(&decks, &winning_cards))
    }
}
