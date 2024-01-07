use std::collections::HashMap;

use lazy_static::lazy_static;

lazy_static!(
    static ref FILE: String = std::fs::read_to_string("../input/day5.txt").unwrap();
);

fn main() {
    let (map_names, seeds, maps) = parse_input(&FILE);
    println!("Part I: Expected 424490994, Result {}", part1(&map_names, &seeds, &maps));
    // println!("Part II: Expected 15290096, Result {}", part2(&map_names, &seeds, &maps));
}

fn part1(map_names: &[&str], seeds: &[i64], maps: &HashMap<&str, Vec<[i64; 3]>>) -> i64 {
    let mut res = i64::MAX;
    for &seed in seeds {
        let mut curr_seed = seed;
        for &name in map_names {
            curr_seed = get_map_to(curr_seed, maps.get(name).unwrap());
        }
        res = res.min(curr_seed);
    }
    res
}

fn get_map_to(seed: i64, ranges: &Vec<[i64; 3]>) -> i64 {
    for &range in ranges {
        if range[0] <= seed && seed <= range[1] {
            return seed + range[2];
        }
    }
    seed
}


fn parse_input(input: &str) -> (Vec<&str>, Vec<i64>, HashMap<&str, Vec<[i64; 3]>>) {
    let map_names = ["seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"];

    let mut lines = input.lines();
    let seeds = lines.next().unwrap().split(": ").nth(1).unwrap().split_whitespace().map(|s| s.parse::<i64>().unwrap()).collect();
    let mut maps: HashMap<&str, Vec<[i64; 3]>> = HashMap::new();
    for key in map_names {
        maps.insert(key, Vec::new());
    }

    lines.next();
    let mut map_id = 0;
    while let Some(_) = lines.next() {
        // lines.next();
        let key = map_names[map_id];
        let ranges = maps.get_mut(key).unwrap();

        let mut low = i64::MAX;
        let mut high = i64::MIN;

        while let Some(line) = lines.next() {
            if line.is_empty() {
                map_id += 1;
                break;
            }
            let entry: Vec<i64> = line.split_whitespace().map(|s| s.parse::<i64>().unwrap()).collect();
            let src = entry[1];
            let dst = src + entry[2] - 1;
            let off_by = entry[0] - src;
            ranges.push([src, dst, off_by]);

            low = low.min(src - 1);
            high = high.max(dst + 1);
        }
        ranges.push([i64::MIN, low, 0]);
        ranges.push([high, i64::MAX, 0]);
    }

    (map_names.to_vec(), seeds, maps)
}
