public class Driver {
	public static void main(String[] args) {
		int N = 3;
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print((i*N + j + 1) + " ");
			}
			System.out.println();
		}
	}
}