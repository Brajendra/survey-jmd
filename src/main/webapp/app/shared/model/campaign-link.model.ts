import dayjs from 'dayjs';
import { ICampaign } from 'app/shared/model/campaign.model';

export interface ICampaignLink {
  id?: number;
  code?: string;
  attemptQuestionCount?: number | null;
  eventType?: string | null;
  eventId?: string | null;
  userInfo?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<ICampaignLink> = {};
