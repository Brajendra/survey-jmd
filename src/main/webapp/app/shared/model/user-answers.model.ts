import dayjs from 'dayjs';

export interface IUserAnswers {
  id?: number;
  campaignLinkId?: number | null;
  answers?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
}

export const defaultValue: Readonly<IUserAnswers> = {};
