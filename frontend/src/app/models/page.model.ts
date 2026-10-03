export interface Page<T> {
  totalPages: number;
  totalElements: number;
  number: number;
  content: T[];
  size: number;
}
