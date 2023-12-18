def part1(file):
    res = 0
    for line in file.readlines():
        for ch in line:
            if ch.isdigit():
                res += int(ch) * 10
                break
        for ch in reversed(line):
            if ch.isdigit():
                res += int(ch)
                break
    return res

def part2(file, words):
    res = 0
    for line in file.readlines():
        res += get_digit(line, words, True) + get_digit(line, words)
    return res

def get_digit(line, words, first=False):
    for i in range(len(line)) if first else reversed(range(len(line))):
        if line[i].isdigit():
            return int(line[i]) * (10 if first else 1)
        for key in words:
            if line.startswith(key, i):
                return words[key] * (10 if first else 1)
    return 0

def main():
    words = {
        'one': 1, 'two': 2, 'three': 3, 'four': 4,
        'five': 5, 'six': 6, 'seven': 7, 'eight': 8, 'nine': 9,
    }
    with open('../input/day1.txt') as file:
        print("Part I  56397 :", part1(file))
    with open('../input/day1.txt') as file:
        print("Part II 55701 :", part2(file, words))

if __name__ == "__main__":
    main()
