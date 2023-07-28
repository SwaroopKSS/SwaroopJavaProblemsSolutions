package com.swaroop.dsa.contests.contest6;

    public class Solution {
        public String SmallestString(String A, int B, int C) {
            int N = A.length();
            B = B % N;
            String smallString = A;
            Queue<String> queue = new LinkedList<>();

            queue.add(A);

            while(!queue.isEmpty()){

                String current = queue.poll();

                if(current.compareTo(smallString){
                    smallString = current;
                }

                //applying rotate operation

                if(B > 0){
                    String rotateString = rotateTheString(current);
                    queue.add(rotateString);
                    B--;
                }

                //applying add operation

                char[] charArray = new char[N];

                for(int i=0;i<N;i++){

                    if(i%2 != 0){
                        int currentDigit = current.charAt(i) - '0';
                        int newDigit = (digit+C) % 10;
                        charArray[i] = (char) (newDigit + '0');
                    }else{
                        charArray[i] = currentDigit.charAt(i);
                    }


                }

                String str = new String(charArray);
                queue.add(str);


            }
            return smallString;

        }

        private String rotateTheString(String str){
            int N = str.length();
            return str.substring(N-1) + str.substring(0, N-1);
        }
    }

}
