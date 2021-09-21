import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toMap;

public class Test {

    private static final int BOUND = 4;
    private static final int SIZE = 4096 * 4096;
    private static final int N_OF_BENCHMARKS = 100;

    record BenchmarkResult(
            String generatorName,
            long durationMillis,
            int someValue
    ) {

        public String format() {
            return "%60s | %20s | %20s".formatted(
                    generatorName,
                    durationMillis,
                    someValue
            );
        }

    }

    record AggregatedBenchmarkResult(String generatorName, double averageDuration) {

        public String format() {
            return "%60s | %20s".formatted(
                    generatorName,
                    "%10.1fms".formatted(averageDuration)
            );
        }

    }


    public static void main(String[] args) {

        System.out.printf("%60s | %20s | %20s%n", "Generator", "Time", "Some value");

        IntStream
                .range(0, N_OF_BENCHMARKS)
                .boxed()
                .flatMap(i -> RandomGeneratorFactory
                        .all()
                        .filter(gf -> !gf
                                .name()
                                .toLowerCase()
                                .contains("secure"))
                        .map(Test::doBenchmark)
                        .peek(x -> System.out.println("%8d".formatted(i) + " | " + x)))
                .collect(toMap(
                        BenchmarkResult::generatorName,
                        br -> new ArrayList<>(List.of(br)),
                        ((results1, results2) -> {
                            var resultList = new ArrayList<BenchmarkResult>();
                            resultList.addAll(results1);
                            resultList.addAll(results2);
                            return resultList;
                        })))
                .entrySet()
                .stream()
                .map(e -> new AggregatedBenchmarkResult(
                        e.getKey(),
                        e.getValue()
                                .stream()
                                .mapToLong(BenchmarkResult::durationMillis)
                                .average()
                                .orElse(0)
                ))
                .sorted(comparingDouble(AggregatedBenchmarkResult::averageDuration))
                .map(AggregatedBenchmarkResult::format)
                .forEach(System.out::println);

    }

    private static BenchmarkResult doBenchmark(RandomGeneratorFactory<RandomGenerator> generatorFactory) {
        var generator = generatorFactory.create();

        int[] ints = new int[SIZE];

        long start = System.nanoTime();

        for (int i = 0; i < SIZE; i++) {
            ints[i] = generator.nextInt(BOUND);
        }

        long end = System.nanoTime();
        long elapsed = end - start;
        Duration duration = Duration.ofNanos(elapsed);

        return new BenchmarkResult(generatorFactory.name(), duration.toMillis(), ints[generator.nextInt(SIZE)]);
    }

}
