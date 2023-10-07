import {Tag} from "./tag.model";

export class GiftCertificate {
  id: number
  name: string
  description: string
  price: number
  duration: number
  photoPath: string | ArrayBuffer | null
  createDate: Date
  lastUpdateDate: Date
  tags: Tag[]
}
