package javapgb;

public class WordStack {

    public int maxSize;// ջ�Ĵ�С
    public int top;
    public String[] arr;

    public WordStack(int size) {
        maxSize = size;
        top = -1;
        arr = new String[maxSize];
    }

    public void push(String value) { // ѹ������

        arr[++top] = value;
    }

    public String pop() { // ��������

        return arr[top--];
    }

    public String peek() { // ����ջ��Ԫ��

        return arr[top];
    }

    public boolean isFull() { // ջ�Ƿ�����

        return maxSize - 1 == top;
    }

    public boolean isEmpty() { // ջ�Ƿ�Ϊ��

        return top == -1;
    }

}