package concurrencia.ej13;

class SelectionSortRunnable implements Runnable {
    private SortingPanel sortingPanel;

    public SelectionSortRunnable(SortingPanel sortingPanel) {
        this.sortingPanel = sortingPanel;
    }

    @Override
    public void run() {
        int[] array = sortingPanel.getArray();
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
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

