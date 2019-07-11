package javapgb;

public class LengthStack {

    public int maxSize;// 栈的大小
    public int top;
    public int[] arr;

    public LengthStack(int size) {
        maxSize = size;
        top = -1;
        arr = new int[maxSize];
    }

    public void push(int value) { // 压入数据

        arr[++top] = value;
    }

    public int pop() { // 弹出数据

        return arr[top--];
    }

    public int peek() { // 访问栈顶元素

        return arr[top];
    }

    public boolean isFull() { // 栈是否满了

        return maxSize - 1 == top;
    }

    public boolean isEmpty() { // 栈是否为空

        return top == -1;
    }

}