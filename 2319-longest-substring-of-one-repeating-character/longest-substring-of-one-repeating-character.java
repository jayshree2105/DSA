class Solution {

    class Node {
        char leftChar;
        char rightChar;

        int prefix;
        int suffix;
        int max;
        int len;

        Node(char c) {
            leftChar = c;
            rightChar = c;

            prefix = 1;
            suffix = 1;
            max = 1;
            len = 1;
        }
    }

    Node[] tree;
    char[] arr;

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {

        arr = s.toCharArray();

        int n = arr.length;

        tree = new Node[4 * n];

        // Build segment tree
        build(1, 0, n - 1);

        int[] answer = new int[queryIndices.length];

        // Process every query
        for (int i = 0; i < queryIndices.length; i++) {

            int index = queryIndices[i];
            char newChar = queryCharacters.charAt(i);

            // Update original array
            arr[index] = newChar;

            // Update segment tree
            update(1, 0, n - 1, index, newChar);

            // Root contains the answer for the whole string
            answer[i] = tree[1].max;
        }

        return answer;
    }

    // ---------------- BUILD ----------------

    void build(int node, int start, int end) {

        // Leaf node
        if (start == end) {

            tree[node] = new Node(arr[start]);

            return;
        }

        int mid = (start + end) / 2;

        // Build left child
        build(node * 2, start, mid);

        // Build right child
        build(node * 2 + 1, mid + 1, end);

        // Merge both children
        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    // ---------------- UPDATE ----------------

    void update(
        int node,
        int start,
        int end,
        int index,
        char newChar
    ) {

        // We reached the required position
        if (start == end) {

            tree[node] = new Node(newChar);

            return;
        }

        int mid = (start + end) / 2;

        // Go to left side
        if (index <= mid) {

            update(
                node * 2,
                start,
                mid,
                index,
                newChar
            );

        } 
        // Go to right side
        else {

            update(
                node * 2 + 1,
                mid + 1,
                end,
                index,
                newChar
            );
        }

        // Recalculate current node
        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    // ---------------- MERGE ----------------

    Node merge(Node left, Node right) {

        Node result = new Node(left.leftChar);

        // First character of complete segment
        result.leftChar = left.leftChar;

        // Last character of complete segment
        result.rightChar = right.rightChar;

        // Total length
        result.len = left.len + right.len;

        // Initially take prefix from left
        result.prefix = left.prefix;

        // Initially take suffix from right
        result.suffix = right.suffix;

        // Maximum is initially max of left and right
        result.max = Math.max(
            left.max,
            right.max
        );

        // If characters at the boundary are same
        if (left.rightChar == right.leftChar) {

            // Sequence can cross the middle
            result.max = Math.max(
                result.max,
                left.suffix + right.prefix
            );

            // Entire left segment has same character
            if (left.prefix == left.len) {

                result.prefix =
                    left.len + right.prefix;
            }

            // Entire right segment has same character
            if (right.suffix == right.len) {

                result.suffix =
                    right.len + left.suffix;
            }
        }

        return result;
    }
}