import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Button } from '../../interface/Button.interface';
import { ButtonComponent } from '../../component/button/button.component';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  logoUrl = 'assets/logo_p6.png';

  buttonPropsRegister: Button = { text: "S'inscrire", colored: false };
  buttonPropsLogin: Button = { text: 'Se connecter', colored: false };

  constructor(private router: Router) {}

  handleLoginClick() {
    this.router.navigateByUrl('/login');
    
  }

  handleRegisterClick() {
    this.router.navigateByUrl('/register');
  }
}
