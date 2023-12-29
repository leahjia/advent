use lazy_static::lazy_static;

lazy_static!(
    static ref FILE: String = std::fs::read_to_string("../input/day3.txt").unwrap();
);
const DIRECT: [(i32, i32); 8] = [(0, 1), (1, 0), (0, -1), (-1, 0), (1, 1), (-1, -1), (-1, 1), (1, -1)];

fn main() {
    println!("{}{}", "Expect 514969, Result: ", part1(&FILE));
    println!("{}{}", "Expect 78915902, Result: ", part2(&FILE));
}

fn part1(input: &str) -> i32 {
    let mut res = 0;
    let mut grid: Vec<Vec<char>> = input.lines().map(|line| line.chars().collect()).collect();

    for r in 0..grid.len() {
        for c in 0..grid[r].len() {
            if !grid[r][c].is_numeric() && grid[r][c] != '.' {
                for &(dr, dc) in &DIRECT {
                    let nr = r as i32 + dr;
                    let nc = c as i32 + dc;
                    if nr >= 0 && nr < grid.len() as i32 && nc >= 0 && nc < grid[r].len() as i32 && grid[nr as usize][nc as usize].is_digit(10) {
                        res += get_nums(&mut grid, nr as usize, nc as usize);
                    }
                }
            }
        }
    }
    res
}

fn get_nums(grid: &mut Vec<Vec<char>>, r: usize, c: usize) -> i32 {
    let mut c = c;
    let col = grid[r].len();
    while c > 0 && grid[r][c - 1].is_digit(10) {
        c -= 1;
    }
    let mut res = String::new();
    while c < col && grid[r][c].is_digit(10) {
        res.push(grid[r][c]);
        grid[r][c] = 'N';
        c += 1;
    }
    res.parse::<i32>().unwrap()
}

fn part2(input: &str) -> i32 {
    let mut res = 0;
    let mut grid: Vec<Vec<char>> = input.lines().map(|line| line.chars().collect()).collect();

    for r in 0..grid.len() {
        for c in 0..grid[r].len() {
            if grid[r][c] == '*' {
                let nums: Vec<i32> = DIRECT.iter().filter_map(|&(dr, dc)| {
                    let (nr, nc) = (r as i32 + dr, c as i32 + dc);
                    if nr >= 0 && nr < grid.len() as i32 && nc >= 0 && nc < grid[r].len() as i32 && grid[nr as usize][nc as usize].is_digit(10) {
                        Some(get_nums(&mut grid, nr as usize, nc as usize))
                    } else {
                        None
                    }
                }).collect();
                if nums.len() == 2 {
                    res += nums[0] * nums[1];
                }
            }
        }
    }
    res
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn day3_part1() {
        assert_eq!(514969, part1(&FILE))
    }

    #[test]
    fn day3_part2() {
        assert_eq!(78915902, part2(&FILE))
    }
}
