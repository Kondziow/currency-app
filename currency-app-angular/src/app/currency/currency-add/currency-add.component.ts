import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CurrencyService} from "../currency.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-currency-add',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './currency-add.component.html',
  styleUrl: './currency-add.component.css'
})
export class CurrencyAddComponent implements OnInit{
  currencyForm: FormGroup;
  response = '';
  error = '';

  constructor(private currencyService: CurrencyService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.initForm();
  }

  onSubmit() {
    this.error = '';
    this.response = '';
    this.currencyService.postCurrencyRequest(this.currencyForm.value)
      .subscribe(response => {
        this.response = response.currencyValue;
      }, error => {
        if (error.error.status == 404) {
          this.error = 'Currency Not Found. Check Currency Name.'
        } else {
          this.error = error.error.error
        }

      })
  }

  onCancel() {
    this.router.navigate(['..'], {relativeTo: this.route});
  }

  initForm() {
    let requesterName = '';
    let currencyName = '';

    this.currencyForm = new FormGroup({
      'requesterName': new FormControl(requesterName, [
        Validators.required,
        Validators.maxLength(100)
      ]),
      'currencyName': new FormControl(currencyName, [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(3)
      ])
    })
  }
}
