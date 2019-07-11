package javapgb;

public class WordStack {

    public int maxSize;// 栈的大小
    public int top;
    public String[] arr;

    public WordStack(int size) {
        maxSize = size;
        top = -1;
        arr = new String[maxSize];
    }

    public void push(String value) { // 压入数据

        arr[++top] = value;
    }

    public String pop() { // 弹出数据

        return arr[top--];
    }

    public String peek() { // 访问栈顶元素

        return arr[top];
    }

    public boolean isFull() { // 栈是否满了

        return maxSize - 1 == top;
    }

    public boolean isEmpty() { // 栈是否为空

        return top == -1;
    }

}