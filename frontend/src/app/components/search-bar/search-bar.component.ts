import { Component, input, output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css',
})
export class SearchBarComponent {
  placeholder = input<string>('Pesquisar...');
  label = input.required<string>();
  searchQuery = signal<string>('');
  search = output<string>();

  onSearch() {
    this.search.emit(this.searchQuery());
  }
}
