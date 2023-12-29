use std::collections::{HashMap, HashSet};
use lazy_static::lazy_static;

lazy_static!(
    static ref FILE: String = std::fs::read_to_string("../input/day4.txt").unwrap();
);

fn main() {
    println!("{}{}", "Expect 21821, Result: ", part1(&FILE));
    println!("{}{}", "Expect 5539496, Result: ", part2(&FILE));
}

fn part1(input: &str) -> i32 {
    let mut res = 0;
    let (winning_cards, curr_cards) = parse_input(input);
    let n_cards = winning_cards.len();
    for i in 0..n_cards {
        let mut ct = 0;
        for card in &curr_cards[i] {
            if winning_cards[i].contains(card) {
                ct *= 2;
                if ct == 0 {
                    ct = 1;
                }
            }
        }
        res += ct;
    }
    res
}

fn part2(input: &str) -> i32 {
    let (winning_cards, curr_cards) = parse_input(input);
    let n_cards = winning_cards.len();

    let mut matches = HashMap::new();
    for i in 0..n_cards {
        for card in &curr_cards[i] {
            if winning_cards[i].contains(card) {
                *matches.entry(i + 1).or_insert(0) += 1;
            }
        }
    }

    let mut res = n_cards as i32;
    let mut q = Vec::new();
    for &key in matches.keys() {
        q.push(key);
    }
    while let Some(curr) = q.pop() {
        for i in 1..=matches[&curr] {
            if matches.contains_key(&(curr + i)) {
                q.push(curr + i);
            }
            res += 1;
        }
    }
    res
}

fn parse_input(input: &str) -> (Vec<HashSet<i32>>, Vec<Vec<i32>>) {
    let mut winning_cards: Vec<HashSet<i32>> = Vec::new();
    let mut curr_cards: Vec<Vec<i32>> = Vec::new();

    for line in input.lines() {
        let parts: Vec<&str> = line.split(": ").collect();
        let mut hand_set = HashSet::new();
        let mut hand_vec = Vec::new();

        let cards: Vec<&str> = parts[1].split(" | ").collect();
        for num in cards[0].trim().split_whitespace() {
            hand_set.insert(num.parse().unwrap());
        }
        for num in cards[1].trim().split_whitespace() {
            hand_vec.push(num.parse().unwrap());
        }

        winning_cards.push(hand_set);
        curr_cards.push(hand_vec);
    }

    (winning_cards, curr_cards)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day4_part1() {
        assert_eq!(21821, part1(&FILE))
    }

    #[test]
    fn day4_part2() {
        assert_eq!(5539496, part2(&FILE))
    }
}
