import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phone'
})
export class PhonePipe implements PipeTransform {

  transform(input: string) {
    const onlyNumbers = input.replace(/\D/g, '');
    return onlyNumbers.replace(/(\d{3})(\d{3})(\d{4})/, '($1)-$2-$3')
  }
}
