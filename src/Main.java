import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    public static void firstMethod(){
        float[] arr = new float[size];
        Arrays.fill(arr,1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void secondMethod(){
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        Arrays.fill(arr,1);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, 0, a1, 0, h);
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, h, a2, 0, h);
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
                }
            }
        });
        long a = System.currentTimeMillis();
        thread1.run();
        thread2.run();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a);
    }
}
