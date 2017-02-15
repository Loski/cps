package FilesPrio;

public class FilePrioContract<T> extends FilePrioDecorator<T>{

	public FilePrioContract(FilesPrio<T> file) {
		super(file);
	}
	
}
