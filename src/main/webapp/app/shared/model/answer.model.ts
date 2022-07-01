import dayjs from 'dayjs';
import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswer {
  id?: number;
  title?: string;
  createdAt?: string | null;
  updatedAt?: string | null;
  question?: IQuestion | null;
}

export const defaultValue: Readonly<IAnswer> = {};
