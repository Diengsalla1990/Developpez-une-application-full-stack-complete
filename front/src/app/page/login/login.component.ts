import { Component } from '@angular/core';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
   standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

   constructor(private router: Router) {}

   backToHome(): void {
    this.router.navigateByUrl('/home');
  }

}
