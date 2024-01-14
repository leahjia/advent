use std::collections::HashMap;
use std::fs;

fn main() {
    let file = fs::read_to_string("../input/day6.txt").unwrap();
    let (time, dist, record) = parse_input(&file);
    println!("Part I: Expected 633080, Result {}", part1(&record));
    println!("Part II: Expected 20048741, Result {}", part2(&time, &dist));
}

fn part1(record: &HashMap<i32, i32>) -> usize {
    record.iter().map(|(&time, &dist)| (0..=time).filter(|&t| (time - t) * t > dist).count()).product()
}

fn part2(time: &Vec<&str>, dist: &Vec<&str>) -> i64 {
    let time_num = time.iter().skip(1).flat_map(|&s| s.chars()).collect::<String>().parse::<i64>().unwrap();
    let dist_num = dist.iter().skip(1).flat_map(|&s| s.chars()).collect::<String>().parse::<i64>().unwrap();
    (0..=time_num).filter(|&t| (time_num - t) * t > dist_num).count() as i64
}

fn parse_input(input: &str) -> (Vec<&str>, Vec<&str>, HashMap<i32, i32>) {
    let mut lines = input.lines();
    let time: Vec<&str> = lines.next().unwrap().split_whitespace().collect();
    let dist: Vec<&str> = lines.next().unwrap().split_whitespace().collect();
    let record: HashMap<i32, i32> = time.iter().zip(dist.iter()).filter_map(|(&t, &d)| t.parse::<i32>().ok().zip(d.parse::<i32>().ok())).collect();
    (time, dist, record)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day6() {
        let file = fs::read_to_string("../input/day6.txt").unwrap();
        let (time, dist, record) = parse_input(&file);
        assert_eq!(633080, part1(&record));
        assert_eq!(20048741, part2(&time, &dist));
    }
}
