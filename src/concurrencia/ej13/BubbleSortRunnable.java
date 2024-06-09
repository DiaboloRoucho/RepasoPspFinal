package concurrencia.ej13;

class BubbleSortRunnable implements Runnable {
    private SortingPanel sortingPanel;

    public BubbleSortRunnable(SortingPanel sortingPanel) {
        this.sortingPanel = sortingPanel;
    }

    @Override
    public void run() {
        int[] array = sortingPanel.getArray();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sortingPanel.updateArray(array);
                    swapped = true;
                    sleep();
                }
            }
        } while (swapped);
    }

    private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

