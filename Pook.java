/*
    @author:peihuangwu
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Pook {
    public static void main(String[] args) {
        Pook pook = new Pook();
        System.out.println(pook.match("2H 3D 5S 9C KD", "2C 3H 4S 8C AH"));
        System.out.println(pook.match("2H 4S 4C 2D 4H", "2S 8S AS QS 3S"));
        System.out.println(pook.match("2H 3D 5S 9C KD", "2C 3H 4S 8C KH"));
    }

    private Map<Character, Integer> map;
    private class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return map.get(o1.charAt(0)) - map.get(o1.charAt(0));
        }
    }

    public Pook() {
        map = new HashMap<>();
        map.put('2', 1);
        map.put('3', 2);
        map.put('4', 3);
        map.put('5', 4);
        map.put('6', 5);
        map.put('7', 6);
        map.put('8', 7);
        map.put('9', 8);
        map.put('T', 9);
        map.put('J', 10);
        map.put('Q', 11);
        map.put('K', 12);
        map.put('A', 13);
    }

    //-1 表示黑牌输给白牌  0表示平局  1表示黑牌赢白牌
    public int match(String black, String white) {
        String[] blacks = black.split("\\s+");
        String[] whites = white.split("\\s+");
        Arrays.sort(blacks, new MyComparator());
        Arrays.sort(whites, new MyComparator());

        int blackType = getType(blacks);
        int whiteType = getType(whites);
        if (blackType > whiteType)
            return 1;
        else if (blackType < whiteType)
            return -1;

        Map<Character, Integer> map1, map2;
        char c1 = '1', c1_2 = '1', c2 = '1', c2_2 = '1';
        int res = 0;

        switch (blackType) {
            case 1:
                return matchScatteredCards(blacks, whites);
            case 2:
                map1 = getNums(blacks);
                map2 = getNums(whites);
                for (char key : map1.keySet()) {
                    if (map1.get(key) == 2) {
                        c1 = key;
                        break;
                    }
                }
                for (char key : map2.keySet()) {
                    if (map2.get(key) == 2) {
                        c2 = key;
                        break;
                    }
                }
                res = map.get(c1).compareTo(map.get(c2));
                return res == 0 ? matchScatteredCards(blacks, whites) : res;
            case 3:
                map1 = getNums(blacks);
                map2 = getNums(whites);
                char single1 = '1', single2 = '1';
                for (char key : map1.keySet()) {
                    if (map1.get(key) == 2) {
                        if (c1 == '1')
                            c1 = key;
                        else {
                            if (map.get(c1) < map.get(key)) {
                                c1_2 = c1;
                                c1 = key;
                            } else {
                                c1_2 = key;
                            }
                        }
                    } else {
                        single1 = key;
                    }
                }
                for (char key : map2.keySet()) {
                    if (map2.get(key) == 2) {
                        if (c2 == '1')
                            c2 = key;
                        else {
                            if (map.get(c2) < map.get(key)) {
                                c2_2 = c2;
                                c2 = key;
                            } else {
                                c2_2 = key;
                            }
                        }
                    } else {
                        single2 = key;
                    }
                }
                if (map.get(c1) > map.get(c2))
                    return 1;
                else if (map.get(c1) < map.get(c2))
                    return -1;
                if (map.get(c1_2) > map.get(c2_2))
                    return 1;
                else if (map.get(c1_2) < map.get(c2_2))
                    return -1;
                return map.get(single1).compareTo(map.get(single2));
            case 4:
                map1 = getNums(blacks);
                map2 = getNums(whites);
                for (char key : map1.keySet()) {
                    if (map1.get(key) == 3) {
                        c1 = key;
                        break;
                    }
                }
                for (char key : map2.keySet()) {
                    if (map2.get(key) == 3) {
                        c2 = key;
                        break;
                    }
                }
                res = map.get(c1).compareTo(map.get(c2));
                return res == 0 ? matchScatteredCards(blacks, whites) : res;
            case 5:
                return map.get(blacks[4].charAt(0)).compareTo(map.get(whites[4].charAt(0)));
            case 6:
                return matchScatteredCards(blacks, whites);
            case 7:
                map1 = getNums(blacks);
                map2 = getNums(whites);
                for (char key : map1.keySet()) {
                    if (map1.get(key) == 3) {
                        c1 = key;
                    } else if (map.get(key) == 2) {
                        c1_2 = key;
                    }
                }
                for (char key : map2.keySet()) {
                    if (map2.get(key) == 3) {
                        c2 = key;
                    } else if (map.get(key) == 2) {
                        c2_2 = key;
                    }
                }
                if (map.get(c1) > map.get(c2))
                    return 1;
                else if (map.get(c1) < map.get(c2))
                    return -1;
                if (map.get(c1_2) > map.get(c2_2))
                    return 1;
                else if (map.get(c1_2) < map.get(c2_2))
                    return -1;
                return 0;
            case 8:
                map1 = getNums(blacks);
                map2 = getNums(whites);
                for (char key : map1.keySet()) {
                    if (map1.get(key) == 4) {
                        c1 = key;
                        break;
                    }
                }
                for (char key : map2.keySet()) {
                    if (map2.get(key) == 4) {
                        c2 = key;
                        break;
                    }
                }
                return c1 > c2 ? 1 : (c1 == c2 ? matchScatteredCards(blacks, whites) : -1);
            case 9:
                return map.get(blacks[4].charAt(0)).compareTo(map.get(whites[4].charAt(0)));
        }
        return 0;
    }

    //获取各个点数的数量
    private Map<Character, Integer> getNums(String[] cards) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(cards[i].charAt(0), map.getOrDefault(cards[i].charAt(0), 0) + 1);
        }
        return map;
    }

    //比较散牌
    private int matchScatteredCards(String[] blacks, String[] whites){
        for (int i = 4; i >= 0; i--) {
            if (map.get(blacks[i].charAt(0)) > map.get(whites[i].charAt(0)))
                return 1;
            else if (map.get(blacks[i].charAt(0)) < map.get(whites[i].charAt(0)))
                return -1;
        }
        return 0;
    }

    //判断牌子类型
    private int getType(String[] cards) {
        //判断是否为同花顺
        boolean flag = true;
        for (int i = 1; i < 5; i++) {
            if ((cards[i].charAt(0) - cards[i - 1].charAt(0)) == 1 && (cards[i].charAt(1) == cards[i - 1].charAt(1)))
                continue;
            else {
                flag = false;
                break;
            }
        }
        if (flag)
            return 9;

        //判断是否为铁支
        int num = 1;
        char c = cards[0].charAt(0);
        for (int i = 1; i < 5; i++) {
            if (cards[i].charAt(0) == c) {
                num++;
                if (num == 4)
                    break;
            } else {
                num = 1;
                c = cards[i].charAt(0);
            }
        }
        if (num == 4)
            return 8;

        //判断是否为葫芦
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(cards[i].charAt(0), map.getOrDefault(cards[i].charAt(0), 0) + 1);
        }
        num = 0;
        for (char key : map.keySet()) {
            if (map.get(key) == 2)
                num += 1;
            else if (map.get(key) == 3)
                num += 2;
        }
        if (num == 3)
            return 7;

        //判断是否为同花
        flag = true;
        for (int i = 1; i < 5; i++) {
            if (cards[i].charAt(1) != cards[i - 1].charAt(1)) {
                flag = false;
                break;
            }
        }
        if (flag)
            return 6;

        //判断是否为顺子
        flag = true;
        for (int i = 1; i < 5; i++) {
            if ((cards[i].charAt(0) - cards[i - 1].charAt(0)) != 1) {
                flag = false;
                break;
            }
        }
        if (flag)
            return 5;

        //判断是否为三条
        for (char key : map.keySet()) {
            if (map.get(key) == 3)
                return 4;
        }

        //判断是否为两对
        for (char key : map.keySet()) {
            if (map.get(key) == 2) {
                if (flag)
                    return 3;
                else
                    flag = true;
            }
        }

        //判断是否为对子
        for (char key : map.keySet()) {
            if (map.get(key) == 2) {
                return 2;
            }
        }

        return 1;
    }
}
