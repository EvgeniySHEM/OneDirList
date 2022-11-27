package ru.avalon.javapp.devj110.onedirlist;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class OneDirList<T> implements Iterable<T> {
    private ListItem<T> head;
    private ListItem<T> tail;

    public OneDirList() {
    }

    private OneDirList(ListItem<T> head, ListItem<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    //    добавление значения в начало списка;
    public void addToHead(T value) {
        if( head != null) {
            ListItem<T> nh = new ListItem<>(value);
            nh.next = head;
            head = nh;
        } else {
            head = tail = new ListItem<>(value);
        }
    }

    //извлечение значения из начала списка без его удаления из списка;
    public T peekFromHead() {
        return head != null ? head.value : null;
    }

    //    извлечение значения из начала списка с удалением из списка;
    public T removeFromHead() {
        if(head == null)
            return null;

        T res = head.value;

        if(head != tail) {
            head = head.next;
        } else {
            head = tail = null;
        }
        return res;
    }

    //добавление значения в конец списка;
    public void addToTail(T value) {
        if(tail != null) {
            tail.next = new ListItem<>(value);
            tail = tail.next;
        } else {
            head = tail = new ListItem<>(value);
        }
    }

    // извлечение значения из конца списка без его удаления;
    public T peekFromTail() {
        return tail != null ? tail.value : null;
    }

    //извлечение значения из конца списка с удалением;
    public T removeFromTail () {
        if(tail == null)
            return null;

        T res = tail.value;

        if( head != tail ) {
            ListItem<T> nt = head;
            while (nt.next != tail)
                nt = nt.next;

            nt.next = null;
            tail = nt;
        } else {
            head = tail = null;
        }
        return res;
    }

    //определение, является ли список пустым, или нет
    public boolean isEmpty() {
        return head == null;
    }

    //определение, содержит ли список заданное значение, или нет
    public boolean contains(Object val) {
        ListItem<T> it = head;
        while (it != null) {
            if(it.keeps(val))
                return true;

            it = it.next;
        }
        return false;
    }

    /**
     * Finds value "val" and remove it from the list.
     * Returns true, if the value has been found and removed,
     * false otherwise
     *
     * @param val value to find and remove
     * @return true, if val has been found and removed, false otherwise
     */
    //удаление заданного значения из списка; если значения в списке нет, то ничего происхо-
    //дить не должно
    public boolean remove(T val) {
        if(head == null)
            return false;

        if(head.keeps(val)) {
            removeFromHead();
            return true;
        }

        ListItem<T> prev = head,
                it = head.next;
        while (it != null) {
            if(it.keeps(val)) {
                if(it != null) {
                    prev.next = it.next;
                } else {
                    removeFromTail();
                }
                return true;
            }
            prev = it;
            it = it.next;
        }
        return false;
    }

    public OneDirList<T> reverse() {
        if(head == null)
            return new OneDirList<>();

        ListItem<T> newTail = new ListItem<>(head.value),
                newHead = newTail,
                it = head;
        while (it.next != null) {
            it = it.next;
            ListItem<T> nh = new ListItem<>(it.value);
            nh.next = newHead;
            newHead = nh;
        }
        return new OneDirList<>(newHead, newTail);
    }

    //печать всех значений списка
    public void printAll() {
        forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        ListItem<T> it = head;
        while (it != null) {
            action.accept(it.value);
            it = it.next;
        }
    }

    public <A> A aggregate(A init, BiFunction<? super A,? super T, ? extends A> aggrFunk) {
        A res = init;
        ListItem<T> it = head;
        while (it != null) {
            res = aggrFunk.apply(res, it.value);
            it = it.next;
        }
        return res;
    }

    @Override
    public Iterator<T> iterator() {
        return new ForwardIterator<>(head);
    }

    private static class ForwardIterator<V> implements Iterator<V>{
        ListItem<V> item;

        ForwardIterator(ListItem<V> item) {
            this.item = item;
        }

        @Override
        public boolean hasNext() {
            return item != null;
        }

        @Override
        public V next() {
            if ( item == null )
                throw new NoSuchElementException();
            V res = item.value;
            item = item.next;
            return res;
        }
    }


    private static class ListItem<V>{
        V value;
        ListItem<V> next;

        ListItem(V value) {
            this.value = value;
        }

        boolean keeps(Object val) {
            return value == null && val == null ||
                    value != null && value.equals(val);
        }
    }


}
