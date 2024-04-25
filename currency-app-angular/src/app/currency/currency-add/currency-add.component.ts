import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CurrencyService} from "../currency.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-currency-add',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './currency-add.component.html',
  styleUrl: './currency-add.component.css'
})
export class CurrencyAddComponent implements OnInit{
  currencyForm: FormGroup;

  constructor(private currencyService: CurrencyService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.initForm();
  }

  onSubmit() {
    this.currencyService.postCurrencyRequest(this.currencyForm.value)
      .subscribe(response => {
        console.log(response)
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

  protected readonly onsubmit = onsubmit;
}
