import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    // Metoda szukająca folderów po nazwie
    public Optional<Folder> findFolderByName(String name) {
        return folders.stream()
                .flatMap(this::flattenFolder)
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    // Metoda zliczająca foldery w strukturze
    public int count() {
        return (int) folders.stream()
                .flatMap(this::flattenFolder)
                .count();
    }

    // Metoda rozwijająca strukturę folderów do pojedynczych streamów
    private Stream<Folder> flattenFolder(Folder folder) {
        if (folder instanceof MultiFolder) {
            return Stream.concat(Stream.of(folder),
                    ((MultiFolder) folder).getFolders().stream().flatMap(this::flattenFolder));
        } else {
            return Stream.of(folder);
        }
    }

    // Metoda szukająca folderów po rozmiarze
    public List<Folder> findFoldersBySize(String size) {
        return folders.stream()
                .flatMap(this::flattenFolder)
                .filter(folder -> folder.getSize().equals(size))
                .collect(Collectors.toList());
    }
}
