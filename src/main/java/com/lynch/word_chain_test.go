package main

import (
	"fmt"
	"math"
	"testing"
)

// https://leetcode.cn/problems/word-ladder-ii/
// 按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列
// 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
// 1. 每对相邻的单词之间仅有单个字母不同。
// 2. 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词。
// 3. sk == endWord
// 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，
// 如果不存在这样的转换序列，返回一个空列表。每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。
//
//	输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
//	输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
//	解释：存在 2 种最短的转换序列：
//	"hit" -> "hot" -> "dot" -> "dog" -> "cog"
//	"hit" -> "hot" -> "lot" -> "log" -> "cog"
func TestWordChain(t *testing.T) {
	//beginWord := "hit"
	//endWord := "cog"
	//wordList := []string{"hot", "dot", "dog", "lot", "log", "cog"}

	//beginWord := "hit"
	//endWord := "cog"
	//wordList := []string{"hot", "dot", "dog", "lot", "log"}

	//beginWord := "qa"
	//endWord := "sq"
	//wordList := []string{"si", "go", "se", "cm", "so", "ph", "mt", "db", "mb", "sb", "kr", "ln", "tm", "le",
	//	"av", "sm", "ar", "ci", "ca", "br", "ti", "ba", "to", "ra", "fa", "yo", "ow", "sn", "ya", "cr", "po", "fe",
	//	"ho", "ma", "re", "or", "rn", "au", "ur", "rh", "sr", "tc", "lt", "lo", "as", "fr", "nb", "yb", "if", "pb",
	//	"ge", "th", "pm", "rb", "sh", "co", "ga", "li", "ha", "hz", "no", "bi", "di", "hi", "qa", "pi", "os", "uh", "wm",
	//	"an", "me", "mo", "na", "la", "st", "er", "sc", "ne", "mn", "mi", "am", "ex", "pt", "io", "be", "fm", "ta", "tb",
	//	"ni", "mr", "pa", "he", "lr", "sq", "ye"}

	beginWord := "cet"
	endWord := "ism"
	wordList := []string{"kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay", "sip", "kay", "per", "val", "mes",
		"ohs", "now", "boa", "cet", "pal", "bar", "die", "war", "hay", "eco", "pub", "lob", "rue", "fry", "lit", "rex", "jan", "cot",
		"bid", "ali", "pay", "col", "gum", "ger", "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark", "has", "zip",
		"fez", "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap", "cry", "led", "abe", "box", "ore", "pig", "fie",
		"toy", "fat", "cal", "lie", "noh", "sew", "ono", "tam", "flu", "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax",
		"jim", "san", "pan", "map", "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct", "old", "fin", "feb", "chi",
		"sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe", "ivy", "fan", "age", "fax", "hip", "jib", "mel", "hus", "sob",
		"ifs", "tab", "ara", "dab", "jag", "jar", "arm", "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk", "far",
		"mew", "wit", "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag", "ron", "soy", "gin", "don", "tug", "fay",
		"vic", "boo", "nam", "ave", "buy", "sop", "but", "orb", "fen", "paw", "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam",
		"pew", "web", "hod", "hun", "gyp", "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere", "dig", "era", "cat",
		"fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new", "aye", "ani", "and", "ibm", "yap", "can", "pyx", "tar",
		"kin", "fog", "hum", "pip", "cup", "dye", "lyx", "jog", "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom",
		"vat", "eat", "pus", "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few", "opt", "poe", "art", "ash", "oar",
		"cap", "lop", "may", "shy", "rid", "bat", "sum", "rim", "fee", "bmw", "sky", "maj", "hue", "thy", "ava", "rap", "den", "fla",
		"auk", "cox", "ibo", "hey", "saw", "vim", "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let", "see", "zit",
		"maw", "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo", "any", "dow", "cod", "bed", "vet", "ham", "sis",
		"hex", "via", "fir", "nod", "mao", "aug", "mum", "hoe", "bah", "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who",
		"bet", "gos", "son", "ear", "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada", "bin", "nil", "mia", "ewe",
		"hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid", "tad", "ken", "wad", "rye", "pap", "bog", "gut", "ito", "woe",
		"our", "ado", "sin", "mad", "ray", "hon", "roy", "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep", "bun",
		"try", "lad", "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip", "geo", "cog", "pas", "zen", "odd", "nan",
		"lay", "pod", "fit", "hem", "joy", "bum", "rio", "yon", "dec", "leg", "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur",
		"sid", "sun", "oil", "red", "doc", "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug", "pop", "tub", "sgt",
		"lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag", "mil", "jig", "hub", "low", "did", "tin", "get", "gte",
		"sox", "lei", "mig", "fig", "lon", "use", "ban", "flo", "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant",
		"net", "tux", "ode", "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic", "ted", "wot", "del", "imp", "cob",
		"way", "ann", "tan", "mci", "job", "wet", "ism", "err", "him", "all", "pad", "hah", "hie", "aim"}

	//beginWord := "hot"
	//endWord := "dog"
	//wordList := []string{"hot", "dog", "dot"}

	res := findLadders3(beginWord, endWord, wordList)
	fmt.Println(res)
}

func findLadders3(beginWord string, endWord string, wordList []string) [][]string {

	wordMap = make(map[string]struct{})
	graph = make(map[string]map[string]struct{}, 0)

	for _, w := range wordList {
		addEdge(w)
	}
	addEdge(beginWord)
	_, ok := wordMap[endWord]
	if !ok {
		return [][]string{}
	}

	result := make([][]string, 0)
	info := Info{path: []string{beginWord}, word: beginWord, visited: map[string]struct{}{}}
	info.visited[beginWord] = struct{}{}
	queue := make([]Info, 0)
	queue = append(queue, info)

	minLevel := math.MaxInt
	for len(queue) > 0 {
		last := queue[len(queue)-1]
		queue = queue[0 : len(queue)-1]
		for w, _ := range graph[last.word] {
			for w1, _ := range graph[w] {
				if _, ok := last.visited[w1]; ok {
					continue
				}
				curLevel := len(last.path) + 1
				if w1 == endWord {
					if minLevel > curLevel {
						minLevel = curLevel
						result = append([][]string{}, append(last.path, w1))
					} else if minLevel == curLevel {
						result = append(result, append(last.path, w1))
					}
					continue
				}
				// 出现更长的匹配则忽略, 否则会出现死循环
				if curLevel > minLevel {
					continue
				}
				n := Info{path: []string{}, word: w1, visited: map[string]struct{}{}}
				n.path = append(n.path, last.path...)
				n.path = append(n.path, w1)
				for k, _ := range last.visited {
					n.visited[k] = struct{}{}
				}
				n.visited[w1] = struct{}{}
				queue = append(queue, n)
			}
		}
	}

	if len(result) == 0 {
		return [][]string{}
	}

	return result
}

var wordMap = make(map[string]struct{})
var graph = make(map[string]map[string]struct{}, 0)

type Info struct {
	path    []string
	word    string
	visited map[string]struct{}
}

func addWord(word string) {
	_, ok := wordMap[word]
	if ok {
		return
	}
	wordMap[word] = struct{}{}
	graph[word] = make(map[string]struct{})
}

func addEdge(word string) {
	addWord(word)
	chars := []byte(word)
	for i, c := range chars {
		chars[i] = '*'
		nw := string(chars)
		addWord(nw)
		if _, ok := graph[word][nw]; !ok {
			graph[word][nw] = struct{}{}
		}
		if _, ok := graph[nw][word]; !ok {
			graph[nw][word] = struct{}{}
		}
		chars[i] = c
	}
}
