import java.util.ListIterator;
import java.util.NoSuchElementException;

class Node<E> {
    E element;
    Node<E> next;
    Node<E> previous;

    public Node(E e) {
        element = e;
    }
}

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size = 0;

    public TwoWayLinkedList() {}

    @Override
    public void add(E e) {
        addLast(e);
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        } else if (index == size) {
            addLast(e);
        } else {
            Node<E> current = getNode(index);
            Node<E> newNode = new Node<>(e);
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
            size++;
        }
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object e) {
        return indexOf(e) >= 0;
    }

    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    @Override
    public int indexOf(Object e) {
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.element.equals(e)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int lastIndexOf(E e) {
        Node<E> current = tail;
        int index = size - 1;
        while (current != null) {
            if (current.element.equals(e)) {
                return index;
            }
            current = current.previous;
            index--;
        }
        return -1;
    }

    @Override
    public boolean remove(Object e) {
        int index = indexOf(e);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        Node<E> removedNode = getNode(index);

        if (removedNode == head) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
        } else if (removedNode == tail) {
            tail = tail.previous;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            removedNode.previous.next = removedNode.next;
            removedNode.next.previous = removedNode.previous;
        }

        size--;
        return removedNode.element;
    }

    @Override
    public E set(int index, E e) {
        Node<E> current = getNode(index);
        E oldElement = current.element;
        current.element = e;
        return oldElement;
    }

    @Override
    public int size() {
        return size;
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new ListIterator<>() {
            private Node<E> current = (index == size) ? null : getNode(index);
            private Node<E> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                lastReturned = current;
                E element = current.element;
                current = current.next;
                return element;
            }

            @Override
            public boolean hasPrevious() {
                return current != null && current.previous != null;
            }

            @Override
            public E previous() {
                if (current == null || current.previous == null) {
                    throw new NoSuchElementException();
                }
                current = current.previous;
                lastReturned = current;
                return current.element;
            }

            @Override
            public int nextIndex() {
                return indexOf(current.element);
            }

            @Override
            public int previousIndex() {
                return indexOf(current.element) - 1;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                TwoWayLinkedList.this.remove(indexOf(lastReturned.element));
                lastReturned = null;
            }

            @Override
            public void set(E e) {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                lastReturned.element = e;
            }

            @Override
            public void add(E e) {
                TwoWayLinkedList.this.add(e);
                lastReturned = null;
            }
        };
    }
}
