use std::{fs, collections::HashMap};

lazy_static::lazy_static! {static ref FILE:String = fs::read_to_string("../input/day1.txt").unwrap();}

fn part1(input: &str) -> i32 {
    input.lines().map(|line| {
        format!("{}{}", 
                line.chars().find(|c| c.is_digit(10)).and_then(|c| c.to_digit(10)).unwrap(), 
                line.chars().rev().find(|c| c.is_digit(10)).and_then(|c| c.to_digit(10)).unwrap()
               ).parse::<i32>().unwrap()
    }).sum()
}

fn part2(input: &str) -> u32 {
    let map = HashMap::from([("one", 1), ("two", 2), ("three", 3), ("four", 4), ("five", 5), ("six", 6), ("seven", 7), ("eight", 8), ("nine", 9)]);

    input.lines().filter_map(|line| {
        let first = (0..line.len()).find_map(|idx| {
            map.iter().find(|&(key, _)| line[idx..].starts_with(key)).map(|(_, &val)| val).or_else(|| line[idx..].chars().next().and_then(|c| c.to_digit(10)))
        }).unwrap();
        let last = (0..line.len()).rev().find_map(|idx| {
            map.iter().find(|&(key, _)| line[idx..].starts_with(key)).map(|(_, &val)| val).or_else(|| line[idx..].chars().next().and_then(|c| c.to_digit(10)))
        }).unwrap();
        Some(first * 10 + last)
    }).sum()
}

fn main() {
    println!("{}{}", "Expect 56397, Result: ", part1(&FILE));
    println!("{}{}", "Expect 55701, Result: ", part2(&FILE));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day1_part1() {
        assert_eq!(56397, part1(&FILE))
    }

    #[test]
    fn day1_part2() {
        assert_eq!(55701, part2(&FILE))
    }
}
