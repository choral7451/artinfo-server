import {Args, Mutation, Query, Resolver} from "@nestjs/graphql";
import {PrismaService} from "@/apis/integration/prisma.service";
import {UserRequest} from "@/apis/user/dto/request/user.request";
import {SuccessResponse} from "@/apis/integration/dto/success.response";
import {UserResponse} from "@/apis/user/dto/response/user.response";
import {UserService} from "@/apis/user/user.service";
import {CreateUserRequest} from "@/apis/user/dto/request/create-user.request";
import {CreateResponse} from "@/apis/integration/dto/create.response";

@Resolver()
export class UserResolver {
  constructor(
    private readonly userService: UserService
  ) {}

  @Query(() => UserResponse)
  async getUser(@Args('input') request: UserRequest) {
    const user = await this.userService.getUser(request.id);
    return UserResponse.fromUser(user)
  }

  @Mutation(() => CreateResponse)
  async createUser(@Args('input') request: CreateUserRequest) {
    const userId = await this.userService.createUser(request)
    return CreateResponse.fromId(userId)
  }
}