package searchengine.services;

import searchengine.config.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinIndex extends RecursiveTask<List<IndexingSite>> {

    private List<IndexingSite> list = new ArrayList<>();

    private List<String> sites = new ArrayList<>();
    private String site;

    public ForkJoinIndex(String site) {this.site=site;}

    public ForkJoinIndex(List<String> sites) {
        this.sites = sites;
    }


    @Override
    protected List<IndexingSite> compute() {

        List<ForkJoinIndex> taskList = new ArrayList<>();
        System.out.printf("Task %s execute in thread %s%n", this, Thread.currentThread().getName());
        try {
            Thread.sleep(1);

        for (String item : sites ) {
            IndexingSite indexingSite = new IndexingSite(item);
            list.add(indexingSite);
            ForkJoinIndex task = new ForkJoinIndex(item);
            task.fork();
            taskList.add(task);
        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for ( ForkJoinIndex task : taskList ) {
            list.addAll(task.join());
        }

        return list;
    }
}
