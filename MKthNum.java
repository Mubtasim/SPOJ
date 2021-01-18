import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Mubtasim Shahriar
 */

public class K-thNumber {

    public static void main(String[] args) {

        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solver solver = new Solver();
//		int t = sc.nextInt();
        int t = 1;
        while (t-- != 0) {
            solver.solve(sc, out);
        }
        out.close();

    }

    static class Solver {
        public void solve(InputReader sc, PrintWriter out) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[][] ara = new int[n][2];
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                int now = sc.nextInt();
                ara[i][0] = now;
                ara[i][1] = i;
                arr[i] = now;
            }
            Arrays.sort(ara, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return Integer.compare(o1[0],o2[0]);
                }
            });
            int[] idxs = new int[n];
            for (int i = 0; i < n; i++) {
                idxs[i] = ara[i][1];
            }
            Seg seg = new Seg(0,n-1,idxs);
            for (int i = 0; i < m; i++) {
                int l = sc.nextInt()-1;
                int r = sc.nextInt()-1;
                int k = sc.nextInt();
                int idx = seg.getIdxQuery(l,r,k);
                out.println(arr[idx]);
            }
        }
    }

    static class Seg {
        int left, right, mid;
        boolean leaf;
        Seg lchild,rchild;
        int[] idx;

        Seg(int l, int r, int[] arr) {
            left = l;
            right = r;
            mid = (l+r)>>1;
            leaf = l==r;
            idx = new int[r-l+1];
            if(leaf) idx[0] = arr[l];
            else {
                lchild = new Seg(l, mid, arr);
                rchild = new Seg(mid+1,r,arr);
                int at = 0;
                int[] lidxs = lchild.idx;
                int[] ridxs = rchild.idx;
                int lp = 0;
                int rp = 0;
                while (lp<lidxs.length && rp<ridxs.length) {
                    if(lidxs[lp]>ridxs[rp])  idx[at++] = ridxs[rp++];
                    else idx[at++] = lidxs[lp++];
                }
                while(lp<lidxs.length) idx[at++] = lidxs[lp++];
                while(rp<ridxs.length) idx[at++] = ridxs[rp++];
            }
        }

        public int getIdxQuery(int l, int r, int k) {
            if(leaf) return idx[0];
            int lHave = lchild.numOfEl(l,r);
            if(lHave<k) return rchild.getIdxQuery(l,r,k-lHave);
            return lchild.getIdxQuery(l,r,k);
        }

        private int numOfEl(int left, int right) {
            int leftAt = -1;
            int l = 0;
            int r = idx.length-1;
            while(l<=r) {
                int mid = (l+r)>>1;
                if(idx[mid]<left) {
                    l = mid+1;
                } else {
                    r = mid-1;
                    leftAt = mid;
                }
            }
            l = 0;
            r = idx.length-1;
            int rightAt = -1;
            while(l<=r) {
                int mid = (l+r)>>1;
                if(idx[mid]<=right) {
                    rightAt = mid;
                    l = mid+1;
                } else {
                    r = mid-1;
                }
            }
            if(leftAt==-1 || rightAt==-1) return 0;
            return rightAt-leftAt+1;
        }
    }
    
    // One can ignore following lines. Just fast scanner implementations.

    static void sort(int[] arr) {
        Random rand = new Random();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = rand.nextInt(n);
            if (idx == i) continue;
            arr[i] ^= arr[idx];
            arr[idx] ^= arr[i];
            arr[i] ^= arr[idx];
        }
        Arrays.sort(arr);
    }

    static void sort(long[] arr) {
        Random rand = new Random();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = rand.nextInt(n);
            if (idx == i) continue;
            arr[i] ^= arr[idx];
            arr[idx] ^= arr[i];
            arr[i] ^= arr[idx];
        }
        Arrays.sort(arr);
    }

    static void sortDec(int[] arr) {
        Random rand = new Random();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = rand.nextInt(n);
            if (idx == i) continue;
            arr[i] ^= arr[idx];
            arr[idx] ^= arr[i];
            arr[i] ^= arr[idx];
        }
        Arrays.sort(arr);
        int l = 0;
        int r = n - 1;
        while (l < r) {
            arr[l] ^= arr[r];
            arr[r] ^= arr[l];
            arr[l] ^= arr[r];
            l++;
            r--;
        }
    }

    static void sortDec(long[] arr) {
        Random rand = new Random();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = rand.nextInt(n);
            if (idx == i) continue;
            arr[i] ^= arr[idx];
            arr[idx] ^= arr[i];
            arr[i] ^= arr[idx];
        }
        Arrays.sort(arr);
        int l = 0;
        int r = n - 1;
        while (l < r) {
            arr[l] ^= arr[r];
            arr[r] ^= arr[l];
            arr[l] ^= arr[r];
            l++;
            r--;
        }
    }

    static class InputReader {
        private boolean finished = false;

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int peek() {
            if (numChars == -1) {
                return -1;
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    return -1;
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private String readLine0() {
            StringBuilder buf = new StringBuilder();
            int c = read();
            while (c != '\n' && c != -1) {
                if (c != '\r') {
                    buf.appendCodePoint(c);
                }
                c = read();
            }
            return buf.toString();
        }

        public String readLine() {
            String s = readLine0();
            while (s.trim().length() == 0) {
                s = readLine0();
            }
            return s;
        }

        public String readLine(boolean ignoreEmptyLines) {
            if (ignoreEmptyLines) {
                return readLine();
            } else {
                return readLine0();
            }
        }

        public BigInteger readBigInteger() {
            try {
                return new BigInteger(nextString());
            } catch (NumberFormatException e) {
                throw new InputMismatchException();
            }
        }

        public char nextCharacter() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            return (char) c;
        }

        public double nextDouble() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new InputMismatchException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public boolean isExhausted() {
            int value;
            while (isSpaceChar(value = peek()) && value != -1) {
                read();
            }
            return value == -1;
        }

        public String next() {
            return nextString();
        }

        public SpaceCharFilter getFilter() {
            return filter;
        }

        public void setFilter(SpaceCharFilter filter) {
            this.filter = filter;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }

        public int[] nextIntArray(int n) {
            int[] array = new int[n];
            for (int i = 0; i < n; ++i) array[i] = nextInt();
            return array;
        }

        public int[] nextSortedIntArray(int n) {
            int array[] = nextIntArray(n);
            Arrays.sort(array);
            return array;
        }

        public int[] nextSumIntArray(int n) {
            int[] array = new int[n];
            array[0] = nextInt();
            for (int i = 1; i < n; ++i) array[i] = array[i - 1] + nextInt();
            return array;
        }

        public long[] nextLongArray(int n) {
            long[] array = new long[n];
            for (int i = 0; i < n; ++i) array[i] = nextLong();
            return array;
        }

        public long[] nextSumLongArray(int n) {
            long[] array = new long[n];
            array[0] = nextInt();
            for (int i = 1; i < n; ++i) array[i] = array[i - 1] + nextInt();
            return array;
        }

        public long[] nextSortedLongArray(int n) {
            long array[] = nextLongArray(n);
            Arrays.sort(array);
            return array;
        }
    }


}
