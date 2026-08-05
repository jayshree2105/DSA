class Solution {
    public boolean isPalindrome(int x) {
        int n = x;
        int revenum=0;
        if(n<0){
            return false ;
        }
        while(n>0){
            int d=n%10;
            revenum=revenum*10+d;
            n=n/10;
        }
        if(x==revenum){
            return true ;
        }
        else {
            return false ;
        }

    }
}