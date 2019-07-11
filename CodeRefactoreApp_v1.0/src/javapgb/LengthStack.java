package javapgb;

public class LengthStack {

    public int maxSize;// ջ�Ĵ�С
    public int top;
    public int[] arr;

    public LengthStack(int size) {
        maxSize = size;
        top = -1;
        arr = new int[maxSize];
    }

    public void push(int value) { // ѹ������

        arr[++top] = value;
    }

    public int pop() { // ��������

        return arr[top--];
    }

    public int peek() { // ����ջ��Ԫ��

        return arr[top];
    }

    public boolean isFull() { // ջ�Ƿ�����

        return maxSize - 1 == top;
    }

    public boolean isEmpty() { // ջ�Ƿ�Ϊ��

        return top == -1;
    }

}