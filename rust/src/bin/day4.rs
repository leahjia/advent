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
        let all_cards: Vec<_> = line.split(": ").nth(1).unwrap().split(" | ").map(|card| card.to_string()).collect();
        winning_cards.push(all_cards.first().unwrap().split_whitespace().map(|card| card.parse::<i32>().unwrap()).collect());
        decks.push(all_cards.get(1).unwrap().split_whitespace().map(|card| card.parse::<i32>().unwrap()).collect());
    }
    (decks, winning_cards)
}

fn part1(decks: &Vec<Vec<i32>>, winning_cards: &Vec<HashSet<i32>>) -> i32 {
    let mut res = 0;
    for i in 0..decks.len() {
        let mut ct = 0;
        for card in decks[i].iter() {
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

fn part2(decks: &Vec<Vec<i32>>, winning_cards: &Vec<HashSet<i32>>) -> i32 {
    let mut matches = HashMap::new();
    for i in 0..decks.len() {
        for card in decks[i].iter() {
            if winning_cards[i].contains(card) {
                matches.insert(i + 1, matches.get(&(i + 1)).unwrap_or(&0) + 1);
            }
        }
    }

    let mut res = decks.len() as i32;
    let mut q: VecDeque<_> = matches.keys().cloned().collect();
    while !q.is_empty() {
        let curr = q.pop_front().unwrap();
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
