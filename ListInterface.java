public interface ListInterface{
  //Adds the given Object at the given index
  //Any objects at position 'index' or later should be moved 1 position right
  void add(int index, Object data);

  //Adds the given object at the end of the list
  void add(Object data);
  
  //Removes and returns the object at given position index
  //Objects positioned later than `
  
  //Sets the object at position index to be the specified Object
  Object set(int index, Object data);
  
  //Returns the object at the given index
  Object get(int index);

  //Returns true if list contains data
  boolean contains(Object data);
  
  //Returns the number of elements in the list
  int size();

  //Removes all items from the list
  void clear();
  
  //Returns true if no items in the list, false otherwise
  boolean isEmpty();  
}