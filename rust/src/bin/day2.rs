use std::{fs, collections::HashMap};

lazy_static::lazy_static! {static ref FILE:String = fs::read_to_string("../input/day2.txt").unwrap();}

fn part1(input: &str, cubes: &HashMap<String, i32>) -> i32 {
    let mut sum: i32 = 0;
    for line in input.lines() {
        let parts: Vec<&str> = line.splitn(2, ":").collect();
        let id: i32 = parts[0].trim().split_whitespace().last().unwrap().parse().unwrap();
        let tokens = parts[1].trim().split(";");
        let mut possible = true;
        for token in tokens {
            let draws = token.trim().split(",");
            for draw in draws {
                let mut iter = draw.split_whitespace();
                if let (Some(count_str), Some(label)) = (iter.next(), iter.next()) {
                    let count: i32 = count_str.parse().unwrap();
                    if count > cubes[label] {
                        possible = false;
                    }
                }
            }
            if !possible {
                break;
            }
        }
        if possible {
            sum += id;
        }
    }
    sum
}

fn part2(input: &str, cubes: &HashMap<String, i32>) -> u32 {
    1
}

fn get_cubes() -> HashMap<String, i32> {
    let mut cubes = HashMap::new();
    cubes.insert("red".to_string(), 12);
    cubes.insert("green".to_string(), 13);
    cubes.insert("blue".to_string(), 14);
    cubes
}

fn main() {
    let cubes = get_cubes();
    println!("{}{}", "Expect 2879, Result: ", part1(&FILE, &cubes));
    println!("{}{}", "Expect 65122, Result: ", part2(&FILE, &cubes));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day2_part1() {
        let cubes = get_cubes();
        assert_eq!(2879, part1(&FILE, &cubes))
    }

    #[test]
    fn day2_part2() {
        let cubes = get_cubes();
        assert_eq!(65122, part2(&FILE, &cubes))
    }
}

