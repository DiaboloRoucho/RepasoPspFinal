package concurrencia.ej13;

class InsertionSortRunnable implements Runnable {
    private SortingPanel sortingPanel;

    public InsertionSortRunnable(SortingPanel sortingPanel) {
        this.sortingPanel = sortingPanel;
    }

    @Override
    public void run() {
        int[] array = sortingPanel.getArray();
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                sortingPanel.updateArray(array);
                sleep();
            }
            array[j + 1] = key;
            sortingPanel.updateArray(array);
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

