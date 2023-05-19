import {Injectable} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import {User} from "@prisma/client";
import * as bcrypt from 'bcrypt';

@Injectable()
export class UserService {
  constructor(
    private readonly prismaService: PrismaService
  ) {}

   async getUser(userId: string): Promise<User> {
    const user = await this.prismaService.user.findFirst({
      where: {id: userId}
    })

    if(!user) {
      throw new Error('USER_DOES_NOT_EXIST');
    }

    return user
   }

   async createUser(fields: CreateUserFields): Promise<string> {
    fields.password = await this.getHashedPassword(fields.password);

    const user = await this.prismaService.user.create({
     data: {
      ...fields
     }
    })

    return user.id
   }

 private getHashedPassword = async (password: string): Promise<string> => {
  return await bcrypt.hash(password, 10);
 };
}