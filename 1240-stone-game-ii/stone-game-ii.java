class Solution {
    public int stoneGameII(int[] piles) {

        int n = piles.length;

        // Suffix sum
        int[] suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // DP table
        int[][] dp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {

            for (int M = 1; M <= n; M++) {

                int best = 0;

                // Alice can take X piles
                for (int X = 1; X <= 2 * M && i + X <= n; X++) {

                    int nextM = Math.max(M, X);

                    int current = suffix[i] - dp[i + X][nextM];

                    best = Math.max(best, current);
                }

                dp[i][M] = best;
            }
        }

        return dp[0][1];
    }
}