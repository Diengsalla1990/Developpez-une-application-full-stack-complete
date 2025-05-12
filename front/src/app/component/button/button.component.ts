import { Component, Input } from '@angular/core';
import { Button } from '../../interface/Button.interface';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.css'
})
export class ButtonComponent {
  @Input() buttonProps!: Button;

}
